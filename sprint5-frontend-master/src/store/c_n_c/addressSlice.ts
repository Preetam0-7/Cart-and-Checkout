// src/store/c_n_c/addressSlice.ts
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import {type Address } from '../../utils/c_n_c/constants';
import axios from 'axios';

const BASE_URL = 'http://localhost:8085/api/checkout/address';

export const fetchAddresses = createAsyncThunk('address/fetchAll', async () => {
  const res = await axios.get(BASE_URL);
  return res.data;
});

export const addAddress = createAsyncThunk('address/add', async (data: Address) => {
  const res = await axios.post(BASE_URL, data);
  return res.data;
});

export const deleteAddress = createAsyncThunk('address/delete', async (id: number) => {
  await axios.delete(`${BASE_URL}/${id}`);
  return id;
});

const addressSlice = createSlice({
  name: 'address',
  initialState: {
    addresses: [] as Address[],
    loading: false,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchAddresses.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchAddresses.fulfilled, (state, action) => {
        state.loading = false;
        state.addresses = action.payload;
      })
      .addCase(addAddress.fulfilled, (state, action) => {
        state.addresses.push(action.payload);
      })
      .addCase(deleteAddress.fulfilled, (state, action) => {
        state.addresses = state.addresses.filter((a) => a.id !== action.payload);
      });
  },
});

export default addressSlice.reducer;
