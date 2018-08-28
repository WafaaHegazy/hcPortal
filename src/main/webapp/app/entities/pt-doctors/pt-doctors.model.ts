import { BaseEntity } from './../../shared';

export class PtDoctors implements BaseEntity {
    constructor(
        public id?: string,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public gender?: string,
        public birthdate?: string
    ) {
    }
}
