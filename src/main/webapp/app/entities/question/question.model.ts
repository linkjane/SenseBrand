import { BaseEntity } from './../../shared';

export class Question implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public content?: any,
    ) {
    }
}
