import AddressForm from '../../components/c_n_c/AddressForm';
import AddressList from '../../components/c_n_c/AddressList';

const AddressPage = () => {
  return (
    <div className="p-4 max-w-2xl mx-auto space-y-6">
      <AddressForm />
      <AddressList />
    </div>
  );
};

export default AddressPage;
