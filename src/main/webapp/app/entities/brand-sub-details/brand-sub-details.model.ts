import { BaseEntity } from './../../shared';

export class BrandSubDetails implements BaseEntity {
    constructor(
        public id?: number,
        public title?: any,
        public introduction?: any,
        public bannerImgFileUrl?: string,
        public bannerImgFileContentType?: string,
        public bannerImgFileBase64Data?: any,
        public bannerImgFileFileSource?: any,
        public bannerImgFile?: any,
        public content?: any,
        public createdTime?: any,
        public brand?: BaseEntity,
        public brandSub?: BaseEntity,
    ) {
    }
}
