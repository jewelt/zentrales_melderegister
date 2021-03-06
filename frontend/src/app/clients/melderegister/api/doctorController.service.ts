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

import { DoctorDTO } from '../model/doctorDTO';
import { PatientDTO } from '../model/patientDTO';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class DoctorControllerService {

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
     * createDoctor
     * 
     * @param body doctorDTO
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public createDoctorUsingPOST(body: DoctorDTO, observe?: 'body', reportProgress?: boolean): Observable<DoctorDTO>;
    public createDoctorUsingPOST(body: DoctorDTO, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DoctorDTO>>;
    public createDoctorUsingPOST(body: DoctorDTO, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DoctorDTO>>;
    public createDoctorUsingPOST(body: DoctorDTO, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling createDoctorUsingPOST.');
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

        return this.httpClient.request<DoctorDTO>('post',`${this.basePath}/v1/doctor`,
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
     * deleteDoctor
     * 
     * @param doctorId doctor-id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteDoctorUsingDELETE(doctorId: number, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public deleteDoctorUsingDELETE(doctorId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public deleteDoctorUsingDELETE(doctorId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public deleteDoctorUsingDELETE(doctorId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (doctorId === null || doctorId === undefined) {
            throw new Error('Required parameter doctorId was null or undefined when calling deleteDoctorUsingDELETE.');
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

        return this.httpClient.request<any>('delete',`${this.basePath}/v1/doctor/${encodeURIComponent(String(doctorId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllDoctors
     * 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllDoctorsUsingGET(observe?: 'body', reportProgress?: boolean): Observable<Array<DoctorDTO>>;
    public getAllDoctorsUsingGET(observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<DoctorDTO>>>;
    public getAllDoctorsUsingGET(observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<DoctorDTO>>>;
    public getAllDoctorsUsingGET(observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

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

        return this.httpClient.request<Array<DoctorDTO>>('get',`${this.basePath}/v1/doctor`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getAllPatientsByDoctor
     * 
     * @param doctorId doctor-id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getAllPatientsByDoctorUsingGET(doctorId: number, observe?: 'body', reportProgress?: boolean): Observable<Array<PatientDTO>>;
    public getAllPatientsByDoctorUsingGET(doctorId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<PatientDTO>>>;
    public getAllPatientsByDoctorUsingGET(doctorId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<PatientDTO>>>;
    public getAllPatientsByDoctorUsingGET(doctorId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (doctorId === null || doctorId === undefined) {
            throw new Error('Required parameter doctorId was null or undefined when calling getAllPatientsByDoctorUsingGET.');
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

        return this.httpClient.request<Array<PatientDTO>>('get',`${this.basePath}/v1/doctor/${encodeURIComponent(String(doctorId))}/patient`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * getDoctor
     * 
     * @param doctorId doctor-id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getDoctorUsingGET(doctorId: number, observe?: 'body', reportProgress?: boolean): Observable<DoctorDTO>;
    public getDoctorUsingGET(doctorId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<DoctorDTO>>;
    public getDoctorUsingGET(doctorId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<DoctorDTO>>;
    public getDoctorUsingGET(doctorId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (doctorId === null || doctorId === undefined) {
            throw new Error('Required parameter doctorId was null or undefined when calling getDoctorUsingGET.');
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

        return this.httpClient.request<DoctorDTO>('get',`${this.basePath}/v1/doctor/${encodeURIComponent(String(doctorId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * updateDoctor
     * 
     * @param body doctorDTO
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public updateDoctorUsingPUT(body: DoctorDTO, observe?: 'body', reportProgress?: boolean): Observable<any>;
    public updateDoctorUsingPUT(body: DoctorDTO, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<any>>;
    public updateDoctorUsingPUT(body: DoctorDTO, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<any>>;
    public updateDoctorUsingPUT(body: DoctorDTO, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {

        if (body === null || body === undefined) {
            throw new Error('Required parameter body was null or undefined when calling updateDoctorUsingPUT.');
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

        return this.httpClient.request<any>('put',`${this.basePath}/v1/doctor`,
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
