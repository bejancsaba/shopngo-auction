/*
 * Ported from Angular 2 ACL project -
 * https://github.com/jsrockstar132/angular2-acl/
 */

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

/**
 * Holds and gives information about the user ACL roles and atomic permissions.
 *
 */
@Injectable()
export class AclService {

    private config = {
        storage: 'localStorage',
        storageKey: 'AclService'
    };

    private data = {
        roles: []
    };

    constructor(private http: HttpClient) {
    }

    /**
     * Restore data from web storage.
     *
     * Returns true if web storage exists and false if it doesn't.
     *
     * @returns {boolean}
     */
    resume(): boolean {
        let storedData;

        switch (this.config.storage) {
            case 'sessionStorage':
                storedData = this.fetchFromStorage('sessionStorage');
                break;
            case 'localStorage':
                storedData = this.fetchFromStorage('localStorage');
                break;
            default:
                storedData = null;
        }
        if (storedData) {
            this.data = Object.assign(this.data, storedData);
            return true;
        }

        return false;
    }

    /**
     * Attach a role to the current user
     *
     * @param role
     */
    attachRole(role): void {
        const trimmedRole = role.replace('role_', '');
        if (this.data.roles.indexOf(trimmedRole) === -1) {
            this.data.roles.push(trimmedRole);
            this.save();
        }
    }

    /**
     * Remove all roles from current user
     */
    flushRoles(): void {
        this.data.roles = [];
        this.save();
    }

    /**
     * Check if the current user has role attached
     *
     * @param role
     * @returns {boolean}
     */
    hasRole(role): boolean {
        return this.data.roles.indexOf(role) > -1;
    }

    /**
     * Persist data to storage based on config
     */
    private save(): void {
        switch (this.config.storage) {
            case 'sessionStorage':
                this.saveToStorage('sessionStorage');
                break;
            case 'localStorage':
                this.saveToStorage('localStorage');
                break;
            default:
                // Don't save
                return;
        }
    }

    /**
     * Persist data to web storage
     */
    private saveToStorage(storagetype: string): void {
        window[storagetype]['setItem'](this.config.storageKey, JSON.stringify(this.data));
    }

    /**
     * Retrieve data from web storage
     */
    private fetchFromStorage(storagetype: string): any {
        const data = window[storagetype]['getItem'](this.config.storageKey);
        return (data) ? JSON.parse(data) : false;
    }
}
