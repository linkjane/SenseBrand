import { BaseEntity } from './../../shared';

export class DesignerShowImg implements BaseEntity {
    constructor(
        public id?: number,
        public imgTile?: any,
        public imgFileUrl?: string,
        public imgFileContentType?: string,
        public imgFileBase64Data?: any,
        public imgFileFileSource?: any,
        public imgFile?: any,
        public designer?: BaseEntity,
    ) {
    }
}
