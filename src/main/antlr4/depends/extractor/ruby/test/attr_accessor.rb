  class SummaryReporter < StatisticsReporter
    attr_accessor :sync
    attr_accessor :old_sync

    def statistics # :nodoc:
      "Finished in %.6fs, %.4f runs/s, %.4f assertions/s." %
        [total_time, count / total_time, assertions / total_time]
    end
end
