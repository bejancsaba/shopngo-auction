import {Injectable} from '@angular/core';
import {Params} from '@angular/router';

@Injectable()
export class UrlCacheService {

    private _cachedUrl = '';
    private _cachedParams: Params;

    constructor() {
    }

    get cachedUrl(): string {
        return this._cachedUrl;
    }

    get cachedParams(): Params {
        return this._cachedParams;
    }

    resetUrl(): void {
        this._cachedUrl = '';
    }

    cacheUrl(url: string): void {
        if (url !== 'login') {
            this._cachedUrl = url;
        }
    }

    cacheParams(params: Params): void {
        if (this.isNotEmptyObject(params)) {
            this._cachedParams = params;
        }
    }

    private isNotEmptyObject(obj): boolean {
        return Object.keys(obj).length > 0;
    }
}
