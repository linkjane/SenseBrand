import { BaseEntity } from './../../shared';

export class Industry implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public solutionLink?: any,
        public analysisLink?: any,
        public inventoryUrl?: string,
        public inventoryContentType?: string,
        public inventoryBase64Data?: any,
        public inventoryFileSource?: any,
        public inventory?: any,
        public isShow?: boolean,
        public industryTops?: BaseEntity[],
        public industryTypes?: BaseEntity[],
    ) {
        this.isShow = false;
    }
}
