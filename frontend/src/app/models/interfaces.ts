export interface User {
    login: string;
    password: string;
}

export interface Point {
    paramX: number,
    paramY: number,
    paramR: number,
}

export interface Attempt {
    x: number,
    y: number,
    r: number,
    hit: boolean
}
