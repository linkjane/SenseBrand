import { BaseEntity } from './../../shared';

export class IndustryFirstClass implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public industrySecondClasses?: BaseEntity[],
        public industryAll?: BaseEntity,
    ) {
    }
}
