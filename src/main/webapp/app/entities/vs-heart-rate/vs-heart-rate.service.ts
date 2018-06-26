import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { VsHeartRate } from './vs-heart-rate.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VsHeartRate>;

@Injectable()
export class VsHeartRateService {

    private resourceUrl =  SERVER_API_URL + 'api/vs-heart-rates';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(vsHeartRate: VsHeartRate): Observable<EntityResponseType> {
        const copy = this.convert(vsHeartRate);
        return this.http.post<VsHeartRate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vsHeartRate: VsHeartRate): Observable<EntityResponseType> {
        const copy = this.convert(vsHeartRate);
        return this.http.put<VsHeartRate>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<VsHeartRate>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VsHeartRate[]>> {
        const options = createRequestOption(req);
        return this.http.get<VsHeartRate[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<VsHeartRate[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VsHeartRate = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VsHeartRate[]>): HttpResponse<VsHeartRate[]> {
        const jsonResponse: VsHeartRate[] = res.body;
        const body: VsHeartRate[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VsHeartRate.
     */
    private convertItemFromServer(vsHeartRate: VsHeartRate): VsHeartRate {
        const copy: VsHeartRate = Object.assign({}, vsHeartRate);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(vsHeartRate.measurmentdate);
        return copy;
    }

    /**
     * Convert a VsHeartRate to a JSON which can be sent to the server.
     */
    private convert(vsHeartRate: VsHeartRate): VsHeartRate {
        const copy: VsHeartRate = Object.assign({}, vsHeartRate);
        return copy;
    }
}
