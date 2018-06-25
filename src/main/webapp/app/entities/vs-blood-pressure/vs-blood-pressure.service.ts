import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { VsBloodPressure } from './vs-blood-pressure.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VsBloodPressure>;

@Injectable()
export class VsBloodPressureService {

    private resourceUrl =  SERVER_API_URL + 'api/vs-blood-pressures';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(vsBloodPressure: VsBloodPressure): Observable<EntityResponseType> {
        const copy = this.convert(vsBloodPressure);
        return this.http.post<VsBloodPressure>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vsBloodPressure: VsBloodPressure): Observable<EntityResponseType> {
        const copy = this.convert(vsBloodPressure);
        return this.http.put<VsBloodPressure>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<VsBloodPressure>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VsBloodPressure[]>> {
        const options = createRequestOption(req);
        return this.http.get<VsBloodPressure[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<VsBloodPressure[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VsBloodPressure = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VsBloodPressure[]>): HttpResponse<VsBloodPressure[]> {
        const jsonResponse: VsBloodPressure[] = res.body;
        const body: VsBloodPressure[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VsBloodPressure.
     */
    private convertItemFromServer(vsBloodPressure: VsBloodPressure): VsBloodPressure {
        const copy: VsBloodPressure = Object.assign({}, vsBloodPressure);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(vsBloodPressure.measurmentdate);
        return copy;
    }

    /**
     * Convert a VsBloodPressure to a JSON which can be sent to the server.
     */
    private convert(vsBloodPressure: VsBloodPressure): VsBloodPressure {
        const copy: VsBloodPressure = Object.assign({}, vsBloodPressure);
        return copy;
    }
}
