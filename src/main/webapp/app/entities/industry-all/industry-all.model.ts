import { BaseEntity } from './../../shared';

export class IndustryAll implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public industryFirstClasses?: BaseEntity[],
    ) {
    }
}
