import { BaseEntity } from './../../shared';

export class Designer implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public englishName?: string,
        public position?: string,
    ) {
    }
}
