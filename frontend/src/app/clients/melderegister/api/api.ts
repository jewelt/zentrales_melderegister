export * from './cityController.service';
import {CityControllerService} from './cityController.service';
import {CountryControllerService} from './countryController.service';
import {DoctorControllerService} from './doctorController.service';
import {PatientControllerService} from './patientController.service';
import {StateControllerService} from './stateController.service';
import {StatisticsControllerService} from './statisticsController.service';
import {TestControllerService} from './testController.service';
import {TestResultControllerService} from './testResultController.service';
import {UserAccountApiControllerService} from './userAccountApiController.service';
import {UserJwtApiControllerService} from './userJwtApiController.service';

export * from './countryController.service';

export * from './doctorController.service';

export * from './patientController.service';

export * from './stateController.service';

export * from './statisticsController.service';

export * from './testController.service';

export * from './testResultController.service';

export * from './userAccountApiController.service';

export * from './userJwtApiController.service';

export const APIS = [CityControllerService, CountryControllerService, DoctorControllerService, PatientControllerService, StateControllerService, StatisticsControllerService, TestControllerService, TestResultControllerService, UserAccountApiControllerService, UserJwtApiControllerService];
