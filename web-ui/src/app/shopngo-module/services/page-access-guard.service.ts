import {Injectable} from '@angular/core';
import {User} from './user.service';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {UrlCacheService} from './url-cache.service';

/**
 * Allow or deny access for application pages (see app-routing.module.ts).
 */
@Injectable()
export class PageAccessGuard implements CanActivate {

    constructor(private user: User,
                private router: Router,
                private urlCacheService: UrlCacheService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        if (this.user.isAuthenticated()) {
            return true;
        }
        else {
            this.urlCacheService.cacheUrl(state.url);
            this.urlCacheService.cacheParams(route.queryParams);
            this.router.navigate(['login']);
            return false;
        }
    }
}
