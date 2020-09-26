import {Injectable} from '@angular/core';
import {AuthJwtServerProvider} from './auth-jwt.service';
import {UserAttributes} from '../model/user-attributes.model';

/**
 * System wide user which uses auth specific services for authentication.
 */
@Injectable()
export class User {

    private userIdentity: UserAttributes;

    constructor(private authJwt: AuthJwtServerProvider) {
    }

    getIdentity(): any {
        if (this.userIdentity == null) {
            this.identity();
        }
        return this.userIdentity;
    }

    authenticate(identity): void {
        this.userIdentity = identity;
    }

    identity(): UserAttributes {
        if (this.isAuthenticated()) {
            this.userIdentity = this.authJwt.getIdentity();
        }
        return null;
    }

    isAuthenticated(): boolean {
        let isTokenNotExpired = false;
        try {
            isTokenNotExpired = !this.authJwt.isTokenExpired(this.authJwt.getToken());
        } catch (e) {
            console.error('Invalid JWT format: ' + e);
        }
        return this.authJwt.getToken() != null && isTokenNotExpired;
    }
}
