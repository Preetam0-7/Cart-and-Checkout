// src/utils/c_n_c/constants.ts
export interface Address {
  id?: number;
  userId?: number;
  addressLane1?: string;
  addressLane2?: string;
  zipcode: string;
  state: string;
  country: string;
  addressType: string;
  contactName: string;
  contactPhoneNumber: string;
}
