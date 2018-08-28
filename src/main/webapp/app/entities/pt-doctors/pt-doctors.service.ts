import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PtDoctors } from './pt-doctors.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PtDoctors>;

@Injectable()
export class PtDoctorsService {

    private resourceUrl =  SERVER_API_URL + 'api/users';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    add(id: string, email: string): Observable<EntityResponseType> {
        return this.http.get<PtDoctors>(`${this.resourceUrl}/addPatient?Mail=${email}&id=${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PtDoctors[]>> {
        const options = createRequestOption(req);
        return this.http.get<PtDoctors[]>(`${this.resourceUrl}/doctors`, { params: options, observe: 'response' })
            .map((res: HttpResponse<PtDoctors[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PtDoctors = res.body;
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PtDoctors[]>): HttpResponse<PtDoctors[]> {
        const jsonResponse: PtDoctors[] = res.body;
        const body: PtDoctors[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(jsonResponse[i]);
        }
        return res.clone({body});
    }

    /**
     * Convert a ptDoctors to a JSON which can be sent to the server.
     */
    private convert(ptDoctors: PtDoctors): PtDoctors {
        const copy: PtDoctors = Object.assign({}, ptDoctors);
        return copy;
    }
}
