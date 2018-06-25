import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DentistNextVisit } from './dentist-next-visit.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DentistNextVisit>;

@Injectable()
export class DentistNextVisitService {

    private resourceUrl =  SERVER_API_URL + 'api/dentist-next-visits';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(dentistNextVisit: DentistNextVisit): Observable<EntityResponseType> {
        const copy = this.convert(dentistNextVisit);
        return this.http.post<DentistNextVisit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dentistNextVisit: DentistNextVisit): Observable<EntityResponseType> {
        const copy = this.convert(dentistNextVisit);
        return this.http.put<DentistNextVisit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<DentistNextVisit>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DentistNextVisit[]>> {
        const options = createRequestOption(req);
        return this.http.get<DentistNextVisit[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<DentistNextVisit[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DentistNextVisit = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DentistNextVisit[]>): HttpResponse<DentistNextVisit[]> {
        const jsonResponse: DentistNextVisit[] = res.body;
        const body: DentistNextVisit[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DentistNextVisit.
     */
    private convertItemFromServer(dentistNextVisit: DentistNextVisit): DentistNextVisit {
        const copy: DentistNextVisit = Object.assign({}, dentistNextVisit);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(dentistNextVisit.measurmentdate);
        return copy;
    }

    /**
     * Convert a DentistNextVisit to a JSON which can be sent to the server.
     */
    private convert(dentistNextVisit: DentistNextVisit): DentistNextVisit {
        const copy: DentistNextVisit = Object.assign({}, dentistNextVisit);
        return copy;
    }
}
