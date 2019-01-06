  self.parallel_executor = Parallel::Executor.new((ENV["N"] || 2).to_i)
