import {Component, OnInit} from '@angular/core';
import {TableColumn} from 'simplemattable';

@Component({
  selector: 'app-liste',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent implements OnInit {

  columns: TableColumn<Test, any>[] = [];
  data: Test[] = [{
    id: 1,
    birthday: '2020-03-16',
    docName: 'Herr Mustermann',
    testDate: '2020-03-16',
    testResult: false
  }];

  constructor() {
  }

  ngOnInit() {
    this.columns = [
      new TableColumn<Test, 'birthday'>('Geburtsdatum', 'birthday'),
      new TableColumn<Test, 'docName'>('Arzt Name', 'docName'),
      new TableColumn<Test, 'testDate'>('Testdatum', 'testDate'),
      new TableColumn<Test, 'testResult'>('Testergbnis', 'testResult'),
    ];
  }

  delete(testToDelete: Test) {
    this.data = this.data.filter(test => test.id !== testToDelete.id);
  }
}

interface Test {
  id: number;
  birthday: string;
  docName: string;
  testDate: string;
  testResult: boolean;
}
