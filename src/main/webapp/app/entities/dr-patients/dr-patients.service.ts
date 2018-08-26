import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DrPatients } from './dr-patients.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DrPatients>;

@Injectable()
export class DrPatientsService {

    private resourceUrl =  SERVER_API_URL + 'api/users';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    add(id: string, email: string): Observable<EntityResponseType> {
        return this.http.get<DrPatients>(`${this.resourceUrl}/addPatient?Mail=${email}&id=${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DrPatients[]>> {
        const options = createRequestOption(req);
        return this.http.get<DrPatients[]>(`${this.resourceUrl}/patients`, { params: options, observe: 'response' })
            .map((res: HttpResponse<DrPatients[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DrPatients = res.body;
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DrPatients[]>): HttpResponse<DrPatients[]> {
        const jsonResponse: DrPatients[] = res.body;
        const body: DrPatients[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(jsonResponse[i]);
        }
        return res.clone({body});
    }

    /**
     * Convert a DrPatients to a JSON which can be sent to the server.
     */
    private convert(drPatients: DrPatients): DrPatients {
        const copy: DrPatients = Object.assign({}, drPatients);
        return copy;
    }
}
