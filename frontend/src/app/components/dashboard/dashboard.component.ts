import {Component, OnDestroy, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {StatisticsControllerService} from '../../clients/melderegister';
import * as moment from 'moment';

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

  // data goes here
  public infectedByState = [];
  public infectedByAge = [];
  public infectedByDay = [];
  public growthByDay = [];
  public growthByStateToday = [];
  public timestamp: string;

  private timer;

  constructor(private breakpointObserver: BreakpointObserver,
              private statisticsControllerService: StatisticsControllerService) {
  }


  onSelect($event: any) {
    console.log($event);
  }

  ngOnInit(): void {
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
  }
}

enum DiagramType {
  ANZAHL_BUNDESLAND = 1,
  ANZAHL_ALTER,
  ANZAHL_TAG,
  ZUWACHS_TAG,
  ZUWACHS_BUNDESLAND
}
