
// todo credits remove this

export const enum Status {
    'paid',
    'pending'

};
export class Credit {
    constructor(
        public id?: number,
        public amount?: number,
        public status?: any,
        public price?: number,
        public timestamp?: any,
        public userId?: number,
    ) {
    }
}
