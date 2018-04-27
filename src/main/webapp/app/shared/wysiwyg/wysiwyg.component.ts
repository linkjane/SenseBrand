import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { STATIC_SERVER_URL, FILE_UPLOAD_URL } from '../../app.constants';

@Component({
  selector: 'jhi-wysiwyg',
  templateUrl: './wysiwyg.component.html',
  styles: []
})
export class WysiwygComponent implements OnInit {

    private _editorContent: string;
    @Input() set editorContent(editorContent: string) {
        this._editorContent = editorContent;
        this.editorContentChange.emit(this._editorContent);
    }

    @Output()
    public editorContentChange: EventEmitter<string> = new EventEmitter<string>();

    private _options: object = {
        placeholderText: 'Edit Your Content Here!',
        language: 'zh_cn',
        theme: 'dark',
        fileUploadURL: FILE_UPLOAD_URL,
        fileUploadParams: {key: 'wysiwyg'},
        // maxsize is 20MB
        fileMaxSize: 20 * 1024 * 1024,
        events : {
            'froalaEditor.file.inserted': (e, editor, response) => {
                response.attr('href', `${STATIC_SERVER_URL}/${response.attr('href')}`);
            }
        }
    };

    @Input() set options(options: object) {
        this._options = Object.assign(this._options, options);
    }

    get options() {
        return this._options;
    }

  constructor() { }

  ngOnInit() {
  }

}
