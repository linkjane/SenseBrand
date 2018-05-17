import { BaseEntity } from './../../shared';

export class Industry implements BaseEntity {
    constructor(
        public id?: number,
        public name?: any,
        public solutionLink?: any,
        public analysisLink?: any,
        public inventoryFileUrl?: string,
        public inventoryFileContentType?: string,
        public inventoryFileBase64Data?: any,
        public inventoryFileFileSource?: any,
        public inventoryFile?: any,
        public isShow?: boolean,
        public industryTops?: BaseEntity[],
        public industryTypes?: BaseEntity[],
    ) {
        this.isShow = false;
    }
}
