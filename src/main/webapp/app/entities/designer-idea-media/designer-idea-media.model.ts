import { BaseEntity } from './../../shared';

export class DesignerIdeaMedia implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public shareTime?: any,
        public introduction?: any,
        public mediaFileUrl?: string,
        public mediaFileContentType?: string,
        public mediaFileBase64Data?: any,
        public mediaFileFileSource?: any,
        public mediaFile?: any,
        public isShow?: boolean,
        public designer?: BaseEntity,
    ) {
        this.isShow = false;
    }
}
