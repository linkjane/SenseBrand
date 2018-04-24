import { BaseEntity } from './../../shared';

export class BrandSub implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public introduction?: any,
        public brandSubDetails?: BaseEntity[],
        public brand?: BaseEntity,
    ) {
    }
}
