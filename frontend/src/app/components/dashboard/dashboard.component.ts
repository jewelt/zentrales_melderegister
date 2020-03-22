import {Component, OnDestroy, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {
  CountryControllerService,
  CountryDTO,
  StateControllerService,
  StateDTO,
  StatisticsControllerService
} from '../../clients/melderegister';
import * as moment from 'moment';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {
  // based on the screen size, switch from standard to one column per row
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {title: 'Anzahl nach Bundesland', cols: 2, rows: 3, type: DiagramType.ANZAHL_BUNDESLAND},
          {title: 'Anzahl nach Alter', cols: 2, rows: 3, type: DiagramType.ANZAHL_ALTER},
          {title: 'Gesamtanzahl nach Tag', cols: 2, rows: 3, type: DiagramType.ANZAHL_TAG},
          {title: 'Heutige Anzahl nach Bundesland', cols: 2, rows: 3, type: DiagramType.ZUWACHS_BUNDESLAND},
          {title: 'Neuinfektionen nach Tag', cols: 2, rows: 3, type: DiagramType.ZUWACHS_TAG}
        ];
      }

      return [
        {title: 'Anzahl nach Bundesland', cols: 1, rows: 1, type: DiagramType.ANZAHL_BUNDESLAND},
        {title: 'Anzahl nach Alter', cols: 1, rows: 1, type: DiagramType.ANZAHL_ALTER},
        {title: 'Gesamtanzahl nach Tag', cols: 1, rows: 1, type: DiagramType.ANZAHL_TAG},
        {title: 'Heutige Anzahl nach Bundesland', cols: 1, rows: 1, type: DiagramType.ZUWACHS_BUNDESLAND},
        {title: 'Neuinfektionen nach Tag', cols: 1, rows: 1, type: DiagramType.ZUWACHS_TAG}
      ];
    })
  );

  diagramType = DiagramType;

  // Search
  showSearch = false;
  states: StateDTO[] = [];
  countries: CountryDTO[] = [];
  searchGroup: FormGroup;

  // Diagram Data
  public infectedByState = [];
  public infectedByAge = [];
  public infectedByDay = [];
  public growthByDay = [];
  public growthByStateToday = [];
  public timestamp: string;

  private timer;

  constructor(private breakpointObserver: BreakpointObserver,
              private fb: FormBuilder,
              private stateControllerService: StateControllerService,
              private countryControllerService: CountryControllerService,
              private statisticsControllerService: StatisticsControllerService) {
  }


  onSelect($event: any) {
    console.log($event);
  }

  ngOnInit(): void {
    this.searchGroup = this.fb.group({
      state: this.fb.control(null),
      country: this.fb.control({value: null, disabled: true}),
      dateFrom: this.fb.control(moment().subtract(7, 'days')),
      dateTill: this.fb.control(moment())
    });
    this.searchGroup.get('state').valueChanges.subscribe((newValue: StateDTO) => {
      const countryControl = this.searchGroup.get('country');
      if (newValue) {
        this.countryControllerService.getAllByCountryIdUsingGET1(newValue.id).subscribe(countries => {
          this.countries = countries;
          if (this.countries.length === 0) {
            countryControl.disable();
          } else {
            countryControl.enable();
          }
        });
      } else {
        this.countries = [];
        countryControl.disable();
      }
    });
    this.loadData();
    this.timer = setInterval(() => {
      this.loadData();
    }, 60000);
  }

  ngOnDestroy(): void {
    clearInterval(this.timer);
  }

  private loadData() {
    this.timestamp = moment().format('DD.MM.YYYY HH:mm');
    this.statisticsControllerService.getCountByStateNowUsingGET().subscribe((data) => {
      this.infectedByState = data;
    });

    this.statisticsControllerService.getCountByAgeUsingGET().subscribe(data => {
      this.infectedByAge = data.map(age => ({
        value: age.value,
        name: age.age
      })).sort((a, b) => {
        if (a.name > b.name) {
          return 1;
        } else if (a.name < b.name) {
          return -1;
        } else {
          return 0;
        }
      });
    });

    this.statisticsControllerService.getCountByDayUsingGET().subscribe(data => {
      this.infectedByDay = data.map(countByDay => ({
        value: countByDay.value,
        name: countByDay.date
      }));
    });

    this.statisticsControllerService.getGrowthByDayUsingGET().subscribe(data => {
      this.growthByDay = data.map(countByDay => ({
        value: countByDay.value,
        name: countByDay.date
      })).sort((a, b) => {
        if (a.name > b.name) {
          return 1;
        } else if (a.name < b.name) {
          return -1;
        } else {
          return 0;
        }
      });
    });

    this.statisticsControllerService.getGrowthByStateTodayUsingGET().subscribe(data => {
      this.growthByStateToday = data;
    });

    this.states = [{
      id: 1,
      name: 'Niedersachsen',
    }, {
      id: 2,
      name: 'Bremen',
    }];

    // this.stateControllerService.getStateDTOUsingGET().subscribe(states => {
    //   this.states = states;
    // });

  }

  search() {

  }
}

enum DiagramType {
  ANZAHL_BUNDESLAND = 1,
  ANZAHL_ALTER,
  ANZAHL_TAG,
  ZUWACHS_TAG,
  ZUWACHS_BUNDESLAND
}
