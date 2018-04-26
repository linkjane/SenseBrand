import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'jhi-wysiwyg',
  templateUrl: './wysiwyg.component.html',
  styles: []
})
export class WysiwygComponent implements OnInit {

    private _editorContent: string;
    @Input() set editorContent(editorContent: string) {
        this._editorContent = editorContent;
    }
    get editorContent() {
        this.editorContentChange.emit(this._editorContent);
        return this._editorContent;
    }
    @Output()
    public editorContentChange: EventEmitter<string> = new EventEmitter<string>();

    private _options: object = {
        placeholderText: 'Edit Your Content Here!',
        language: 'zh_cn',
        theme: 'dark',
        fileUploadURL: 'http://localhost:8080/api/file-upload/upload',
        fileUploadParams: {key: 'wysiwyg'},
        // maxsize is 20MB
        fileMaxSize: 20 * 1024 * 1024,
        events : {
            'froalaEditor.file.inserted': (e, editor, response) => {
                response.attr('href', `http://localhost:8089/${response.attr('href')}`);
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
