import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  private loadingStatus = {
    pending: 0,
    recentlyFinished: 0,
  };

  public loadingStatusObservable = new BehaviorSubject<LoadingStatus>(this.loadingStatus);

  constructor() {
  }

  addPending() {
    this.loadingStatus.pending++;
    this.loadingStatusObservable.next(this.loadingStatus);
  }

  removePending() {
    this.loadingStatus.pending--;
    setTimeout(() => {
      if (this.loadingStatus.pending === 0) {
        this.loadingStatus.recentlyFinished = 0;
      } else {
        this.loadingStatus.recentlyFinished++;
      }
      this.loadingStatusObservable.next(this.loadingStatus);
    }, 100);
    this.loadingStatusObservable.next(this.loadingStatus);
  }
}

export interface LoadingStatus {
  pending: number;
  recentlyFinished: number;
}
