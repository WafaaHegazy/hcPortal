import { BaseEntity } from './../../shared';

export class BloodTest implements BaseEntity {
    constructor(
        public id?: string,
        public userid?: string,
        public hydroxyprogesterone17?: number,
        public hydroxyvitaminD25?: number,
        public acetoacetate?: number,
        public acidity?: number,
        public alcohol?: number,
        public ammonia?: number,
        public amylase?: number,
        public ascorbicAcid?: number,
        public bicarbonate?: number,
        public bilirubin?: number,
        public bloodVolume?: number,
        public calcium?: number,
        public carbonDioxidePressure?: number,
        public carbonMonoxide?: number,
        public cD4CellCount?: number,
        public ceruloplasmin?: number,
        public chloride?: number,
        public completeBloodCellCount?: number,
        public copper?: number,
        public creatineKinase?: number,
        public creatineKinaseIsoenzymes?: number,
        public creatinine?: number,
        public electrolytes?: number,
        public erythrocyteSedimentationRate?: number,
        public glucose?: number,
        public hematocrit?: number,
        public hemoglobin?: number,
        public iron?: number,
        public ironBindingCapacity?: number,
        public lactate?: number,
        public lacticDehydrogenase?: number,
        public lead?: number,
        public lipase?: number,
        public zinc?: number,
        public lipidsCholesterol?: number,
        public lipidsTriglycerides?: number,
        public magnesium?: number,
        public meanCorpuscularHemoglobin?: number,
        public meanCorpuscularHemoglobinConcentration?: number,
        public meanCorpuscularVolume?: number,
        public osmolality?: number,
        public oxygenPressure?: number,
        public oxygenSaturation?: number,
        public phosphataseProstatic?: number,
        public phosphatase?: number,
        public phosphorus?: number,
        public plateletCount?: number,
        public potassium?: number,
        public prostateSpecificAntigen?: number,
        public proteinsTotal?: number,
        public proteinsAlbumin?: number,
        public proteinsGlobulin?: number,
        public prothrombin?: number,
        public pyruvicAcid?: number,
        public redBloodCellCount?: number,
        public sodium?: number,
        public thyroidStimulatingHormone?: number,
        public transaminaseAlanine?: number,
        public transaminaseAspartate?: number,
        public ureaNitrogen?: number,
        public bUNCreatinineRatio?: number,
        public uricAcid?: number,
        public vitaminA?: number,
        public wBC?: number,
        public whiteBloodCellCount?: number,
        public measurmentdate?: any,
    ) {
    }
}
