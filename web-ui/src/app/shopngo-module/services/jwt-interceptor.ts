import {Injectable, Injector} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {AuthJwtServerProvider} from './auth-jwt.service';
import {Observable} from 'rxjs';
import {APP_NAME, AUTHENTICATION_URL, AUTHORIZATION_HEADER_NAME, BEARER_PREFIX} from '../constants/app.config';
import {tap} from 'rxjs/operators';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private injector: Injector) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const auth: AuthJwtServerProvider = this.injector.get(AuthJwtServerProvider);
        const token = auth.getToken();
        const setHeadersObject = {
            Client: APP_NAME
        };

        if (token) {
            setHeadersObject[AUTHORIZATION_HEADER_NAME] = `${BEARER_PREFIX} ${token}`;
        }
        const req = request.clone({setHeaders: setHeadersObject});

        return next.handle(req).pipe(
            tap((event: HttpEvent<any>) => {
                if (this.canRefreshToken(event)) {
                    auth.refreshToken((<HttpResponse<any>>event));
                }
            }));
    }

    private canRefreshToken(event: HttpEvent<any>): boolean {
        return event instanceof HttpResponse
            && !event.url.includes(AUTHENTICATION_URL)
            && !!event.headers.get(AUTHORIZATION_HEADER_NAME);
    }
}
