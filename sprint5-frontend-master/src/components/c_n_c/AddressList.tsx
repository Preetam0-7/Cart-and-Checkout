import { useEffect } from 'react';
import './AddresssList.css';
import { useAppDispatch, useAppSelector } from '../../hooks/c_n_c/useTypedHooks';
import { fetchAddresses, deleteAddress } from '../../store/c_n_c/addressSlice';


const AddressList = () => {
  const dispatch = useAppDispatch();
  const { addresses, loading } = useAppSelector((state) => state.address);

  useEffect(() => {
    dispatch(fetchAddresses());
  }, [dispatch]);

  return (
    <div>
      <h2>Saved Addresses</h2>
      {loading ? (
        <p>Loading...</p>
      ) : (
        addresses.map((a) => (
          <div key={a.id}>
            <p>{a.contactName} ({a.addressType})</p>
            <p>{a.addressLane1}, {a.addressLane2}</p>
            <p>{a.state}, {a.zipcode}, {a.country}</p>
            <p>{a.contactPhoneNumber}</p>
            <button
              onClick={() => dispatch(deleteAddress(a.id!))}
            >
              Delete
            </button>
          </div>
        ))
      )}
    </div>
  );
};

export default AddressList;
