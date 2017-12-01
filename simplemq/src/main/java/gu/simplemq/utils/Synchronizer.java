package gu.simplemq.utils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 同步接口
 * @author guyadong
 *
 */
public interface Synchronizer
{
    /**
     * Notifies this {@code Synchronizer} that the current thread is going to
     * start a read operation on the managed configuration. This call can block
     * if a concrete implementation decides that the thread has to wait until a
     * specific condition is fulfilled.
     */
    void beginRead();

    /**
     * Notifies this {@code Synchronizer} that the current thread has finished
     * its read operation. This may cause other waiting threads to be granted
     * access to the managed configuration.
     */
    void endRead();

    /**
     * Notifies this {@code Synchronizer} that the current thread is going to
     * start a write operation on the managed configuration. This call may
     * block. For instance, a concrete implementation may suspend the thread
     * until all read operations currently active are finished,
     */
    void beginWrite();

    /**
     * Notifies this {@code Synchronizer} that the current thread has finished
     * its write operation. This may cause other waiting threads to be granted
     * access to the managed configuration.
     */
    void endWrite();
    /**
     * {@link Synchronizer}接口空实例
     *
     */
    public static final  Synchronizer NO_OP_SYNCHRONIZER  = new  Synchronizer()
    {
        @Override
        public void beginRead()
        {
        }

        @Override
        public void endRead()
        {
        }

        @Override
        public void beginWrite()
        {
        }

        @Override
        public void endWrite()
        {
        }
    };
    /**
     * 基于{@link ReentrantReadWriteLock }实现同步接口
     * @author guyadong
     *
     */
    public static class ReadWriteSynchronizer implements Synchronizer
    {
        /** The lock object used by this Synchronizer. */
        private final ReadWriteLock lock;

        /**
         * Creates a new instance of {@code ReadWriteSynchronizer} and initializes
         * it with the given lock object. This constructor can be used to pass a
         * lock object which has been configured externally. If the lock object is
         * <b>null</b>, a default lock object is created.
         *
         * @param l the lock object to be used (can be <b>null</b>)
         */
        public ReadWriteSynchronizer(ReadWriteLock l)
        {
            lock = (l != null) ? l : createDefaultLock();
        }

        /**
         * Creates a new instance of {@code ReadWriteSynchronizer} and initializes
         * it with a lock object of type {@code ReentrantReadWriteLock}.
         */
        public ReadWriteSynchronizer()
        {
            this(null);
        }

        @Override
        public void beginRead()
        {
            lock.readLock().lock();
        }

        @Override
        public void endRead()
        {
            lock.readLock().unlock();
        }

        @Override
        public void beginWrite()
        {
            lock.writeLock().lock();
        }

        @Override
        public void endWrite()
        {
            lock.writeLock().unlock();
        }

        /**
         * Returns a new default lock object which is used if no lock is passed to
         * the constructor.
         *
         * @return the new default lock object
         */
        private static ReadWriteLock createDefaultLock()
        {
            return new ReentrantReadWriteLock();
        }
    }
}
