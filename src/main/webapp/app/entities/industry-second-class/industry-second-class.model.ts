import { BaseEntity } from './../../shared';

export class IndustrySecondClass implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public brands?: BaseEntity[],
        public industryFirstClass?: BaseEntity,
    ) {
    }
}
