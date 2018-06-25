import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { VsSpo2 } from './vs-spo-2.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VsSpo2>;

@Injectable()
export class VsSpo2Service {

    private resourceUrl =  SERVER_API_URL + 'api/vs-spo-2-s';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(vsSpo2: VsSpo2): Observable<EntityResponseType> {
        const copy = this.convert(vsSpo2);
        return this.http.post<VsSpo2>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(vsSpo2: VsSpo2): Observable<EntityResponseType> {
        const copy = this.convert(vsSpo2);
        return this.http.put<VsSpo2>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<VsSpo2>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VsSpo2[]>> {
        const options = createRequestOption(req);
        return this.http.get<VsSpo2[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<VsSpo2[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VsSpo2 = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VsSpo2[]>): HttpResponse<VsSpo2[]> {
        const jsonResponse: VsSpo2[] = res.body;
        const body: VsSpo2[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VsSpo2.
     */
    private convertItemFromServer(vsSpo2: VsSpo2): VsSpo2 {
        const copy: VsSpo2 = Object.assign({}, vsSpo2);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(vsSpo2.measurmentdate);
        return copy;
    }

    /**
     * Convert a VsSpo2 to a JSON which can be sent to the server.
     */
    private convert(vsSpo2: VsSpo2): VsSpo2 {
        const copy: VsSpo2 = Object.assign({}, vsSpo2);
        return copy;
    }
}
