import axios from "axios";

const BASE_URL = "http://localhost:8083/api/addresses"; // Replace with actual API base if different

export const getUserAddresses = async (userId: number) => {
  try {
    const response = await axios.get(`${BASE_URL}/user/${userId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching user addresses:", error);
    throw error;
  }
};

export const deleteAddress = async (addressId: number) => {
  try {
    await axios.delete(`${BASE_URL}/${addressId}`);
  } catch (error) {
    console.error("Error deleting address:", error);
    throw error;
  }
};

import type { Address } from '../../utils/c_n_c/constants';

export const updateAddress = async (addressId: number, data: Address) => {
  try {
    const response = await axios.put(`${BASE_URL}/${addressId}`, data);
    return response.data;
  } catch (error) {
    console.error("Error updating address:", error);
    throw error;
  }
};

export const setDefaultAddress = async (userId: number, addressId: number) => {
  try {
    const response = await axios.post(`${BASE_URL}/default`, {
      userId,
      addressId,
    });
    return response.data;
  } catch (error) {
    console.error("Error setting default address:", error);
    throw error;
  }
};
