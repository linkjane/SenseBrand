import { BaseEntity } from './../../shared';

export class Designer implements BaseEntity {
    constructor(
        public id?: number,
        public znName?: any,
        public enName?: any,
        public profileBackFileUrl?: string,
        public profileBackFileContentType?: string,
        public profileBackFileBase64Data?: any,
        public profileBackFileFileSource?: any,
        public profileBackFile?: any,
        public profileThumbnailFileUrl?: string,
        public profileThumbnailFileContentType?: string,
        public profileThumbnailFileBase64Data?: any,
        public profileThumbnailFileFileSource?: any,
        public profileThumbnailFile?: any,
        public position?: any,
        public introduction?: any,
        public isShow?: boolean,
        public designerShowImgs?: BaseEntity[],
        public designerSentiments?: BaseEntity[],
        public designerAwards?: BaseEntity[],
        public designerIdeaMedias?: BaseEntity[],
        public designerIdeaDetails?: BaseEntity,
        public designerShow?: BaseEntity,
    ) {
        this.isShow = false;
    }
}
