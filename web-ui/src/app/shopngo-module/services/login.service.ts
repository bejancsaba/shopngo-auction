import {Injectable} from '@angular/core';
import {AuthJwtServerProvider} from './auth-jwt.service';
import {User} from './user.service';

@Injectable()
export class LoginService {

    constructor(private user: User, private authServerProvider: AuthJwtServerProvider) {
    }

    login(credentials, callback?: any): Promise<any> {
        const cb = callback || function (): void {
        };

        return new Promise((resolve, reject) => {
            this.authServerProvider
                .login(credentials)
                .subscribe(
                    data => {
                        this.user.identity();
                        resolve(data);
                        return cb();
                    },
                    err => {
                        this.logout();
                        reject(err);
                        return cb(err);
                    });
        });
    }

    logout(): void {
        this.authServerProvider.logout().subscribe();
        this.user.authenticate(null);
    }
}
