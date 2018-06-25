import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { VsBodyTemperature } from './vs-body-temperature.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VsBodyTemperature>;

@Injectable()
export class VsBodyTemperatureService {

    private resourceUrl =  SERVER_API_URL + 'api/vs-body-temperatures';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(vsBodyTemperature: VsBodyTemperature): Observable<EntityResponseType> {
        const copy = this.convert(vsBodyTemperature);
        return this.http.post<VsBodyTemperature>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vsBodyTemperature: VsBodyTemperature): Observable<EntityResponseType> {
        const copy = this.convert(vsBodyTemperature);
        return this.http.put<VsBodyTemperature>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<VsBodyTemperature>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VsBodyTemperature[]>> {
        const options = createRequestOption(req);
        return this.http.get<VsBodyTemperature[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<VsBodyTemperature[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VsBodyTemperature = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VsBodyTemperature[]>): HttpResponse<VsBodyTemperature[]> {
        const jsonResponse: VsBodyTemperature[] = res.body;
        const body: VsBodyTemperature[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VsBodyTemperature.
     */
    private convertItemFromServer(vsBodyTemperature: VsBodyTemperature): VsBodyTemperature {
        const copy: VsBodyTemperature = Object.assign({}, vsBodyTemperature);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(vsBodyTemperature.measurmentdate);
        return copy;
    }

    /**
     * Convert a VsBodyTemperature to a JSON which can be sent to the server.
     */
    private convert(vsBodyTemperature: VsBodyTemperature): VsBodyTemperature {
        const copy: VsBodyTemperature = Object.assign({}, vsBodyTemperature);
        return copy;
    }
}
