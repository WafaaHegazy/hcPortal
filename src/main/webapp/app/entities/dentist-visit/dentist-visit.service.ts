import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DentistVisit } from './dentist-visit.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DentistVisit>;

@Injectable()
export class DentistVisitService {

    private resourceUrl =  SERVER_API_URL + 'api/dentist-visits';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(dentistVisit: DentistVisit): Observable<EntityResponseType> {
        const copy = this.convert(dentistVisit);
        return this.http.post<DentistVisit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dentistVisit: DentistVisit): Observable<EntityResponseType> {
        const copy = this.convert(dentistVisit);
        return this.http.put<DentistVisit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<DentistVisit>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DentistVisit[]>> {
        const options = createRequestOption(req);
        return this.http.get<DentistVisit[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<DentistVisit[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DentistVisit = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DentistVisit[]>): HttpResponse<DentistVisit[]> {
        const jsonResponse: DentistVisit[] = res.body;
        const body: DentistVisit[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DentistVisit.
     */
    private convertItemFromServer(dentistVisit: DentistVisit): DentistVisit {
        const copy: DentistVisit = Object.assign({}, dentistVisit);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(dentistVisit.measurmentdate);
        return copy;
    }

    /**
     * Convert a DentistVisit to a JSON which can be sent to the server.
     */
    private convert(dentistVisit: DentistVisit): DentistVisit {
        const copy: DentistVisit = Object.assign({}, dentistVisit);
        return copy;
    }
}
