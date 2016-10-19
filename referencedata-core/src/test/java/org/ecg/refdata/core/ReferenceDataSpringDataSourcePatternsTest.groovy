package org.ecg.refdata.core

import static org.easymock.classextension.EasyMock.*
import org.ecg.refdata.IReferenceDataConfig
import org.ecg.refdata.IDictionaryConfig
import org.ecg.refdata.exceptions.NoSuchDictionaryException;

/**
 * @author Henryk Konsek
 */
class ReferenceDataSpringDataSourcePatternsTest extends GroovyTestCase {
  void testNoPattern() {
    // given
    def dictId = 'foo'
    def dictIds = ['foo' : createNiceMock(IDictionaryConfig.class)]

    def mock = createNiceMock(IReferenceDataConfig)
    expect(mock.getDictionaryConfigMap()).andReturn(dictIds)
    replay mock
    def ds = new ReferenceDataSpringDataSource(mock)

    // when
    ds.getDictionaryConfig(dictId)

    // then
    assertNotNull ds.getDictionaryConfig(dictId)
    try {
      ds.getDictionaryConfig('bar')
    } catch(NoSuchDictionaryException e) {
      assert true
      return
    }
    fail()
  }

    void testPattern() {
    // given
    def pattern = 'foo.*'
    def dictPatterns = ['foo.*' : createNiceMock(IDictionaryConfig.class)]

    def mock = createNiceMock(IReferenceDataConfig)
    expect(mock.getDictionaryConfigMap()).andReturn(dictPatterns)
    replay mock
    def ds = new ReferenceDataSpringDataSource(mock)

    // when
    ds.getDictionaryConfig(pattern)

    // then
    assertNotNull ds.getDictionaryConfig(pattern)
    assertNotNull ds.getDictionaryConfig("foo")
    assertNotNull ds.getDictionaryConfig("foo1")
      try {
      ds.getDictionaryConfig('prefix_foo')
    } catch(NoSuchDictionaryException e) {
      assert true
      return
    }
    fail()
  }

   void testConstantBeforePattern() {
    // given
    def constantConfigMock = createMock(IDictionaryConfig)
    expect(constantConfigMock.getPreferredColumns()).andReturn((String[])[])
    replay constantConfigMock

    def dictPatterns = ['foo.*' : constantConfigMock, 'foo1' : createNiceMock(IDictionaryConfig) ]

    def mock = createNiceMock(IReferenceDataConfig)
    expect(mock.getDictionaryConfigMap()).andReturn(dictPatterns)
    replay mock
    def ds = new ReferenceDataSpringDataSource(mock)

    // when
    def dc = ds.getDictionaryConfig('foo.*')

    // then
    assertEquals([], dc.getPreferredColumns())
    verify constantConfigMock
  }

}
