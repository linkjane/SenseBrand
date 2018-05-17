import { BaseEntity } from './../../shared';

export class IndustryType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public smallTitle?: any,
        public industryTypeNames?: BaseEntity[],
    ) {
    }
}
