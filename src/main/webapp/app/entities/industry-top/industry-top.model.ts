import { BaseEntity } from './../../shared';

export class IndustryTop implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public goLink?: any,
        public industry?: BaseEntity,
    ) {
    }
}
