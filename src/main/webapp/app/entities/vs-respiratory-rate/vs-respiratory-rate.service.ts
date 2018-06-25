import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { VsRespiratoryRate } from './vs-respiratory-rate.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VsRespiratoryRate>;

@Injectable()
export class VsRespiratoryRateService {

    private resourceUrl =  SERVER_API_URL + 'api/vs-respiratory-rates';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(vsRespiratoryRate: VsRespiratoryRate): Observable<EntityResponseType> {
        const copy = this.convert(vsRespiratoryRate);
        return this.http.post<VsRespiratoryRate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vsRespiratoryRate: VsRespiratoryRate): Observable<EntityResponseType> {
        const copy = this.convert(vsRespiratoryRate);
        return this.http.put<VsRespiratoryRate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<VsRespiratoryRate>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VsRespiratoryRate[]>> {
        const options = createRequestOption(req);
        return this.http.get<VsRespiratoryRate[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<VsRespiratoryRate[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VsRespiratoryRate = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VsRespiratoryRate[]>): HttpResponse<VsRespiratoryRate[]> {
        const jsonResponse: VsRespiratoryRate[] = res.body;
        const body: VsRespiratoryRate[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VsRespiratoryRate.
     */
    private convertItemFromServer(vsRespiratoryRate: VsRespiratoryRate): VsRespiratoryRate {
        const copy: VsRespiratoryRate = Object.assign({}, vsRespiratoryRate);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(vsRespiratoryRate.measurmentdate);
        return copy;
    }

    /**
     * Convert a VsRespiratoryRate to a JSON which can be sent to the server.
     */
    private convert(vsRespiratoryRate: VsRespiratoryRate): VsRespiratoryRate {
        const copy: VsRespiratoryRate = Object.assign({}, vsRespiratoryRate);
        return copy;
    }
}
