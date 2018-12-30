    def write_handshake_request(encoder)
      local_hash = local_protocol.md5
      remote_name = transport.remote_name
      remote_hash = REMOTE_HASHES[remote_name]
      unless remote_hash
        remote_hash = local_hash
        self.remote_protocol = local_protocol
      end
      request_datum = {
        'clientHash' => local_hash,
        'serverHash' => remote_hash
      }
      if send_protocol
        request_datum['clientProtocol'] = local_protocol.to_s
      end
      HANDSHAKE_REQUESTOR_WRITER.write(request_datum, encoder)
    end

