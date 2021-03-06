/**
 * Zentrales Melderegister
 * Zentrales Melderegister
 *
 * OpenAPI spec version: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *//* tslint:disable:no-unused-variable member-ordering */

import { Inject, Injectable, Optional }                      from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,
         HttpResponse, HttpEvent }                           from '@angular/common/http';
import { CustomHttpUrlEncodingCodec }                        from '../encoder';

import { Observable }                                        from 'rxjs';

import { CreateUserRequest } from '../model/createUserRequest';
import { UserAccountDTO } from '../model/userAccountDTO';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class UserAccountApiControllerService {

    protected basePath = '//localhost:8080/';
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(protected httpClient: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     * @param consumes string[] mime-types
     * @return true: consumes contains 'multipart/form-data', false: otherwise
     */
    private canConsumeForm(consumes: string[]): boolean {
        const form = 'multipart/form-data';
        for (const consume of consumes) {
            if (form === consume) {
                return true;
            }
        }
        return false;
    }


    /**
     * deleteUser
     * 
     * @param userAccountId userAccountId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteUserUsingDELETE(userAccountId: number, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public deleteUserUsingDELETE(userAccountId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public deleteUserUsingDELETE(userAccountId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public deleteUserUsingDELETE(userAccountId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (userAccountId === null || userAccountId === undefined) {
            throw new Error('Required parameter userAccountId was null or undefined when calling deleteUserUsingDELETE.');
        }

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<any>('delete',`${this.basePath}/api/account/${encodeURIComponent(String(userAccountId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllAccounts
     * 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllAccountsUsingGET(observe?: 'body', reportProgress?: boolean): Observable<Array<UserAccountDTO>>;
    public getAllAccountsUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<UserAccountDTO>>>;
    public getAllAccountsUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<UserAccountDTO>>>;
    public getAllAccountsUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<Array<UserAccountDTO>>('get',`${this.basePath}/api/accounts`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getCurrentUser
     * 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getCurrentUserUsingGET(observe?: 'body', reportProgress?: boolean): Observable<UserAccountDTO>;
    public getCurrentUserUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<UserAccountDTO>>;
    public getCurrentUserUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<UserAccountDTO>>;
    public getCurrentUserUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<UserAccountDTO>('get',`${this.basePath}/api/account`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * isActivated
     * 
     * @param activationKey activationKey
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public isActivatedUsingGET(activationKey: string, observe?: 'body', reportProgress?: boolean): Observable<boolean>;
    public isActivatedUsingGET(activationKey: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<boolean>>;
    public isActivatedUsingGET(activationKey: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<boolean>>;
    public isActivatedUsingGET(activationKey: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (activationKey === null || activationKey === undefined) {
            throw new Error('Required parameter activationKey was null or undefined when calling isActivatedUsingGET.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (activationKey !== undefined && activationKey !== null) {
            queryParameters = queryParameters.set('activationKey', <any>activationKey);
        }

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<boolean>('get',`${this.basePath}/api/user/activated`,
            {
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * isAuthenticated
     * 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public isAuthenticatedUsingGET(observe?: 'body', reportProgress?: boolean): Observable<string>;
    public isAuthenticatedUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<string>>;
    public isAuthenticatedUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<string>>;
    public isAuthenticatedUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<string>('get',`${this.basePath}/api/authenticate`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * isResetting
     * 
     * @param resetKey resetKey
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public isResettingUsingGET(resetKey: string, observe?: 'body', reportProgress?: boolean): Observable<boolean>;
    public isResettingUsingGET(resetKey: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<boolean>>;
    public isResettingUsingGET(resetKey: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<boolean>>;
    public isResettingUsingGET(resetKey: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (resetKey === null || resetKey === undefined) {
            throw new Error('Required parameter resetKey was null or undefined when calling isResettingUsingGET.');
        }

        let queryParameters = new HttpParams({encoder: new CustomHttpUrlEncodingCodec()});
        if (resetKey !== undefined && resetKey !== null) {
            queryParameters = queryParameters.set('resetKey', <any>resetKey);
        }

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
        ];

        return this.httpClient.request<boolean>('get',`${this.basePath}/api/user/resetting`,
            {
                params: queryParameters,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * registerAccount
     * 
     * @param body createUserRequest
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public registerAccountUsingPOST(body: CreateUserRequest, observe?: 'body', reportProgress?: boolean): Observable<UserAccountDTO>;
    public registerAccountUsingPOST(body: CreateUserRequest, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<UserAccountDTO>>;
    public registerAccountUsingPOST(body: CreateUserRequest, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<UserAccountDTO>>;
    public registerAccountUsingPOST(body: CreateUserRequest, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling registerAccountUsingPOST.');
        }

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            '*/*'
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<UserAccountDTO>('post',`${this.basePath}/api/register`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * registerAdminAccount
     * 
     * @param body createUserRequest
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public registerAdminAccountUsingPOST(body: CreateUserRequest, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public registerAdminAccountUsingPOST(body: CreateUserRequest, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public registerAdminAccountUsingPOST(body: CreateUserRequest, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public registerAdminAccountUsingPOST(body: CreateUserRequest, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling registerAdminAccountUsingPOST.');
        }

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<any>('post',`${this.basePath}/api/register-admin`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * updateUser
     * 
     * @param body userAccount
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public updateUserUsingPUT(body: UserAccountDTO, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public updateUserUsingPUT(body: UserAccountDTO, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public updateUserUsingPUT(body: UserAccountDTO, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public updateUserUsingPUT(body: UserAccountDTO, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling updateUserUsingPUT.');
        }

        let headers = this.defaultHeaders;

        // authentication (JWT) required
        if (this.configuration.apiKeys["X-Melderegister-Authorization"]) {
            headers = headers.set('X-Melderegister-Authorization', this.configuration.apiKeys["X-Melderegister-Authorization"]);
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
        ];
        const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set('Accept', httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        const consumes: string[] = [
            'application/json'
        ];
        const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set('Content-Type', httpContentTypeSelected);
        }

        return this.httpClient.request<any>('put',`${this.basePath}/api/user`,
            {
                body: body,
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
