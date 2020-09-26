import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {AUTH_TOKEN_NAME, AUTHENTICATION_URL, AUTHORIZATION_HEADER_NAME} from '../constants/app.config';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';
import {UserAttributes} from '../model/user-attributes.model';
import {AclService} from './acl.service';

/**
 * This class is responsible for handling the JWT authentication
 * and storing the JWT tokens.
 */
@Injectable()
export class AuthJwtServerProvider {
    private jwtHelper: JwtHelperService = new JwtHelperService();

    constructor(private http: HttpClient, private aclService: AclService) {
    }

    getToken(): any {
        return localStorage.getItem(this.getTokenName());
    }

    getTokenName(): string {
        return AUTH_TOKEN_NAME;
    }

    login(credentials): Observable<any> {
        localStorage.removeItem('caseType');
        const data = {
            username: credentials.username,
            password: credentials.password
        };
        return this.http.post(AUTHENTICATION_URL, data, {observe: 'response'})
            .pipe(
                map((resp) => {
                    this.authenticateSuccess(resp);
                })
            );
    }

    logout(): Observable<any> {
        return new Observable(observer => {
            localStorage.removeItem(this.getTokenName());
            sessionStorage.removeItem(this.getTokenName());
            observer.complete();
        });
    }

    isTokenExpired(jwtToken): boolean {
        return this.jwtHelper.isTokenExpired(jwtToken);
    }

    refreshToken(httpResponseEvent: HttpResponse<any>): void {
        this.authenticateSuccess(httpResponseEvent);
    }

    private authenticateSuccess(resp): void {
        const bearerToken = resp.headers.get(AUTHORIZATION_HEADER_NAME);
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
            const jwt = bearerToken.slice(7, bearerToken.length);
            if (this.isTokenExpired(jwt)) {
                throw new Error('Your JWT token has expired!');
            } else {
                this.storeAuthenticationToken(jwt);
                this.storeRoles(jwt);
            }
        }
    }

    private storeAuthenticationToken(jwt: any): void {
        localStorage.setItem(this.getTokenName(), jwt);
    }

    private storeRoles(jwt: any): void {
        const userAttributes: UserAttributes = this.jwtHelper.decodeToken(jwt);
        this.aclService.flushRoles();
        userAttributes.auth.split(',').forEach(role => this.aclService.attachRole(role.toLowerCase()));
    }

    getIdentity(): UserAttributes {
        this.storeRoles(this.getToken());
        return this.jwtHelper.decodeToken(this.getToken());
    }
}
