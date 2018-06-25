import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MenstrualCycle } from './menstrual-cycle.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MenstrualCycle>;

@Injectable()
export class MenstrualCycleService {

    private resourceUrl =  SERVER_API_URL + 'api/menstrual-cycles';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(menstrualCycle: MenstrualCycle): Observable<EntityResponseType> {
        const copy = this.convert(menstrualCycle);
        return this.http.post<MenstrualCycle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(menstrualCycle: MenstrualCycle): Observable<EntityResponseType> {
        const copy = this.convert(menstrualCycle);
        return this.http.put<MenstrualCycle>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<MenstrualCycle>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MenstrualCycle[]>> {
        const options = createRequestOption(req);
        return this.http.get<MenstrualCycle[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<MenstrualCycle[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MenstrualCycle = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MenstrualCycle[]>): HttpResponse<MenstrualCycle[]> {
        const jsonResponse: MenstrualCycle[] = res.body;
        const body: MenstrualCycle[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MenstrualCycle.
     */
    private convertItemFromServer(menstrualCycle: MenstrualCycle): MenstrualCycle {
        const copy: MenstrualCycle = Object.assign({}, menstrualCycle);
        copy.startDate = this.dateUtils
            .convertDateTimeFromServer(menstrualCycle.startDate);
        copy.endDate = this.dateUtils
            .convertDateTimeFromServer(menstrualCycle.endDate);
        return copy;
    }

    /**
     * Convert a MenstrualCycle to a JSON which can be sent to the server.
     */
    private convert(menstrualCycle: MenstrualCycle): MenstrualCycle {
        const copy: MenstrualCycle = Object.assign({}, menstrualCycle);
        return copy;
    }
}
