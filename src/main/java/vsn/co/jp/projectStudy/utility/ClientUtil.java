package vsn.co.jp.projectStudy.utility;

import java.sql.Timestamp;

import vsn.co.jp.projectStudy.DBModel.ClientDetail;
import vsn.co.jp.projectStudy.DBModel.ClientMaster;
import vsn.co.jp.projectStudy.model.Client;
import vsn.co.jp.projectStudy.repository.ClientDetailRepository;
import vsn.co.jp.projectStudy.repository.ClientMasterRepository;

public class ClientUtil {

    public static void addClient(Client client,
            ClientMasterRepository clientMaster,
            ClientDetailRepository clientDetail) {

        int count = clientMaster.getNextId();

        String id = StringUtil.createClientId(count);

        client.setClientId(id);

        ClientMaster master = new ClientMaster();
        master.setClient_id(id);

        ClientDetail detail = new ClientDetail();
        detail.setClient_id(id);

        saveOrUpdateClient(client, master, detail, clientMaster, clientDetail);

    }

    public static void updateClient(Client client,
            ClientMasterRepository clientMaster,
            ClientDetailRepository clientDetail) {

        ClientMaster master = clientMaster.findById(client.getClientId()).get();

        ClientDetail detail = clientDetail.findById(client.getClientId()).get();

        saveOrUpdateClient(client, master, detail, clientMaster, clientDetail);

    }

    public static void saveOrUpdateClient(Client client, ClientMaster master,
            ClientDetail detail, ClientMasterRepository clientMaster,
            ClientDetailRepository clientDetail) {

        master.setClient_code(client.getClientCode());

        clientMaster.save(master);

        detail.setClient_name(client.getClientName());
        detail.setOpen_flg(client.isOpenFlag());
        detail.setUser_name_flg(client.isUserNameFlg());
        detail.setAddress_flg(client.isAddressFlg());
        detail.setTel_number_flg(client.isTelNumberFlg());
        detail.setMail_address_flg(client.isMailAddressFlg());
        detail.setAdd_timestamp_flg(client.isAddTimeStampFlg());
        detail.setLast_login_time_flg(client.isLastLoginTimeFlg());
        detail.setAdd_timestamp(new Timestamp(System.currentTimeMillis()));
        detail.setRedirect_address(client.getRedirectUrl());
        detail.setMemo(client.getMemo());

        clientDetail.save(detail);
    }

    public static Client createResponseClientData(ClientMaster master,
            ClientDetail detail) throws Exception {
        Client client = new Client(master.getClient_id(),
                detail.getClient_name(), master.getClient_code(),
                detail.isOpen_flg(), detail.isUser_name_flg(),
                detail.isAddress_flg(), detail.isTel_number_flg(),
                detail.isMail_address_flg(), detail.isAdd_timestamp_flg(),
                detail.isLast_login_time_flg(), detail.getRedirect_address(),
                detail.getMemo());

        return client;
    }
}
