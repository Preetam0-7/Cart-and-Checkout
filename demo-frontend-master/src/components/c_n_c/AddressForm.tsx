import { useState } from 'react';
import { useAppDispatch } from '../../hooks/c_n_c/useTypedHooks';
import { addAddress } from '../../store/c_n_c/addressSlice';
import {type Address } from '../../utils/c_n_c/constants';

const AddressForm = () => {
  const dispatch = useAppDispatch();
  const [form, setForm] = useState<Address>({
    addressLane1: '',
    addressLane2: '',
    zipcode: '',
    state: '',
    country: '',
    addressType: '',
    contactName: '',
    contactPhoneNumber: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    dispatch(addAddress(form));
    setForm({
      addressLane1: '',
      addressLane2: '',
      zipcode: '',
      state: '',
      country: '',
      addressType: '',
      contactName: '',
      contactPhoneNumber: '',
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-2">
      {Object.entries(form).map(([key, value]) => (
        <input
          key={key}
          name={key}
          value={value}
          onChange={handleChange}
          placeholder={key}
          className="border p-2 rounded w-full"
          required
        />
      ))}
      <button className="bg-blue-600 text-white px-4 py-2 rounded">Add Address</button>
    </form>
  );
};

export default AddressForm;
